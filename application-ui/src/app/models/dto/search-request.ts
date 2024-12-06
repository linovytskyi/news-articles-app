export class SearchRequest {
    text: string;
    topic: string;
    keywords: string[];
    source: string;
    startDate: Date;
    endDate: Date;
    searchDate: Date;
    pageNumber: number;
    pageSize: number
}
