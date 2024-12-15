import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {LoginRequest} from '../../models/auth/login-request';
import {RegisterRequest} from '../../models/auth/register-request';
import {LocalStorageService} from '../../services/local-storage.service';
import {Router} from '@angular/router';
import {HttpErrorResponse, HttpStatusCode} from '@angular/common/http';
import {AuthenticationResponse} from '../../models/auth/authentication-response';
import {UserService} from '../../services/user.service';

export enum AuthenticationOption {
  AUTHENTICATION,
  REGISTRATION
}

@Component({
  selector: 'app-login-page',
  templateUrl: './auth-page.component.html',
  styleUrls: ['./auth-page.component.css']
})
export class AuthPageComponent implements OnInit {

  public selectedAuthenticationOption: AuthenticationOption;
  public loginRequest: LoginRequest;
  public registerRequest: RegisterRequest;

  public registrationPassword: string;
  public submitRegistrationPassword: string;
  public showErrorMessage = false;
  public errorMessage: string;
  public emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  constructor(private authService: AuthService,
              private localStorageService: LocalStorageService,
              private userService: UserService,
              private router: Router) {
  }

  public ngOnInit(): void {
    this.selectedAuthenticationOption = AuthenticationOption.AUTHENTICATION;
    this.loginRequest = this.buildLoginRequest();
    this.registerRequest = this.buildRegisterRequest();
  }

  public selectAuthenticationOption(option: AuthenticationOption): void {
    this.selectedAuthenticationOption = option;
  }

  public registerUser(): void {
    this.authService.register(this.registerRequest).subscribe(
      response => this.handleSuccessfulRegistration(response),
      error => this.handleRegistrationError(error))
  }

  private handleSuccessfulRegistration(response: AuthenticationResponse): void {
    this.localStorageService.saveAuthToken(response.token);
    this.parseAuthToken(response.token);
    this.router.navigate(['/news-feed']);
  }

  private handleRegistrationError(error: HttpErrorResponse): void {
    if (error.status === HttpStatusCode.Conflict) {
      this.handleUserAlreadyExistsError();
    }
  }

  private handleUserAlreadyExistsError(): void {
    this.errorMessage = 'Користувач з такою електронною поштою вже зареєстрований';
    this.showErrorMessage = true;
  }

  public loginUser(): void {
    this.authService.login(this.loginRequest).subscribe(
      response => this.handleSuccessfulLogin(response),
      error => this.handleLoginError(error)
    )
  }

  private handleSuccessfulLogin(response: AuthenticationResponse): void {
    this.localStorageService.saveAuthToken(response.token);
    this.parseAuthToken(response.token);
    this.router.navigate(['feed']);
  }

  private parseAuthToken(token: any): void {
    const user = this.userService.buildUserFromToken(token);
    this.userService.setUser(user);
  }

  private handleLoginError(error: HttpErrorResponse): void {
    if (error.status === HttpStatusCode.Unauthorized) {
      this.handleUnauthorizedError();
    } else if (error.status === HttpStatusCode.NotFound) {
      this.handleUserNotFoundError();
    }
  }

  private handleUnauthorizedError(): void {
    this.errorMessage = 'Неправильна електронна пошта або пароль';
    this.showErrorMessage = true;
  }

  private handleUserNotFoundError(): void {
    this.errorMessage = `Користувача з такою електронною поштою не знайдено`;
    this.showErrorMessage = true;
  }

  private buildLoginRequest(): LoginRequest {
    const loginRequest = new LoginRequest();
    loginRequest.email = null;
    loginRequest.password = null;
    return loginRequest;
  }

  private buildRegisterRequest(): RegisterRequest {
    const registerRequest = new RegisterRequest();
    registerRequest.email = null;
    registerRequest.password = null;
    return registerRequest;
  }

  public shouldLoginButtonBeDisabled(): boolean {
    const result =  !(this.isEmailValid(this.loginRequest.email) && this.isPasswordValid(this.loginRequest.password));
    return result;
  }

  public shouldRegisterButtonBeDisabled(): boolean {
    return !(this.isEmailValid(this.registerRequest.email) && this.areRegistrationPasswordSameAndValid());
  }

  private areRegistrationPasswordSameAndValid(): boolean {
    if (this.isPasswordValid(this.registrationPassword) && this.isPasswordValid(this.submitRegistrationPassword)) {
      return this.registrationPassword === this.submitRegistrationPassword;
    }
    return false;
  }

  private isPasswordValid(password: string): boolean {
    return password && password.trim().length >= 5;
  }

  public isEmailValid(email: string) {
    return this.emailRegex.test(email);
  }

  protected readonly AuthenticationOption = AuthenticationOption;
}
