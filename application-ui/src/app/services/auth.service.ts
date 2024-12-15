import {Injectable} from "@angular/core";
import {LocalStorageService} from "./local-storage.service";
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../models/auth/register-request";
import {LoginRequest} from "../models/auth/login-request";
import {Observable} from "rxjs";
import {AuthenticationResponse} from "../models/auth/authentication-response";

@Injectable({
  providedIn:'root'
})
export class AuthService {
  private constructor(private http: HttpClient,
                      private localStorageService: LocalStorageService) {

  }

  public register(request: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>("http://localhost:8081/api/v1/auth/register", request);
  }

  public login(request: LoginRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>("http://localhost:8081/api/v1/auth/authenticate", request);
  }

  public logout(): void {
    return this.localStorageService.removeAuthToken();
  }

  public checkRegistration(): Observable<string> {
    return this.http.get("http://localhost:8081/api/v1/demo-controller", {
      responseType:'text',
    });
  }

  public isUserAuthenticated(): Promise<boolean> {
    return new Promise((resolve) => resolve(!!this.localStorageService.getAuthToken()))
  }

}
