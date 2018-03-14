import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { Test2Component } from './test-2.component';
import { Test2DetailComponent } from './test-2-detail.component';
import { Test2PopupComponent } from './test-2-dialog.component';
import { Test2DeletePopupComponent } from './test-2-delete-dialog.component';

@Injectable()
export class Test2ResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const test2Route: Routes = [
    {
        path: 'test-2',
        component: Test2Component,
        resolve: {
            'pagingParams': Test2ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test2S'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-2/:id',
        component: Test2DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test2S'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const test2PopupRoute: Routes = [
    {
        path: 'test-2-new',
        component: Test2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test2S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-2/:id/edit',
        component: Test2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test2S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-2/:id/delete',
        component: Test2DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test2S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
