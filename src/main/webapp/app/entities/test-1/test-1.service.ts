import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Test1 } from './test-1.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Test1>;

@Injectable()
export class Test1Service {

    private resourceUrl =  SERVER_API_URL + 'api/test-1-s';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/test-1-s';

    constructor(private http: HttpClient) { }

    create(test1: Test1): Observable<EntityResponseType> {
        const copy = this.convert(test1);
        return this.http.post<Test1>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(test1: Test1): Observable<EntityResponseType> {
        const copy = this.convert(test1);
        return this.http.put<Test1>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Test1>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Test1[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test1[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test1[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Test1[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test1[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test1[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Test1 = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Test1[]>): HttpResponse<Test1[]> {
        const jsonResponse: Test1[] = res.body;
        const body: Test1[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Test1.
     */
    private convertItemFromServer(test1: Test1): Test1 {
        const copy: Test1 = Object.assign({}, test1);
        return copy;
    }

    /**
     * Convert a Test1 to a JSON which can be sent to the server.
     */
    private convert(test1: Test1): Test1 {
        const copy: Test1 = Object.assign({}, test1);
        return copy;
    }
}
