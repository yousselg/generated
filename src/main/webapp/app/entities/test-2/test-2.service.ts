import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Test2 } from './test-2.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Test2>;

@Injectable()
export class Test2Service {

    private resourceUrl =  SERVER_API_URL + 'api/test-2-s';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/test-2-s';

    constructor(private http: HttpClient) { }

    create(test2: Test2): Observable<EntityResponseType> {
        const copy = this.convert(test2);
        return this.http.post<Test2>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(test2: Test2): Observable<EntityResponseType> {
        const copy = this.convert(test2);
        return this.http.put<Test2>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Test2>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Test2[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test2[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test2[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Test2[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test2[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test2[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Test2 = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Test2[]>): HttpResponse<Test2[]> {
        const jsonResponse: Test2[] = res.body;
        const body: Test2[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Test2.
     */
    private convertItemFromServer(test2: Test2): Test2 {
        const copy: Test2 = Object.assign({}, test2);
        return copy;
    }

    /**
     * Convert a Test2 to a JSON which can be sent to the server.
     */
    private convert(test2: Test2): Test2 {
        const copy: Test2 = Object.assign({}, test2);
        return copy;
    }
}
