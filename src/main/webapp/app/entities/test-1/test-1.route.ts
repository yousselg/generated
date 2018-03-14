import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { Test1Component } from './test-1.component';
import { Test1DetailComponent } from './test-1-detail.component';
import { Test1PopupComponent } from './test-1-dialog.component';
import { Test1DeletePopupComponent } from './test-1-delete-dialog.component';

@Injectable()
export class Test1ResolvePagingParams implements Resolve<any> {

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

export const test1Route: Routes = [
    {
        path: 'test-1',
        component: Test1Component,
        resolve: {
            'pagingParams': Test1ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test1S'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-1/:id',
        component: Test1DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test1S'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const test1PopupRoute: Routes = [
    {
        path: 'test-1-new',
        component: Test1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test1S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-1/:id/edit',
        component: Test1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test1S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-1/:id/delete',
        component: Test1DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test1S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
