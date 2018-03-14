import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhispterSharedModule } from '../../shared';
import {
    Test2Service,
    Test2PopupService,
    Test2Component,
    Test2DetailComponent,
    Test2DialogComponent,
    Test2PopupComponent,
    Test2DeletePopupComponent,
    Test2DeleteDialogComponent,
    test2Route,
    test2PopupRoute,
    Test2ResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...test2Route,
    ...test2PopupRoute,
];

@NgModule({
    imports: [
        JhispterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        Test2Component,
        Test2DetailComponent,
        Test2DialogComponent,
        Test2DeleteDialogComponent,
        Test2PopupComponent,
        Test2DeletePopupComponent,
    ],
    entryComponents: [
        Test2Component,
        Test2DialogComponent,
        Test2PopupComponent,
        Test2DeleteDialogComponent,
        Test2DeletePopupComponent,
    ],
    providers: [
        Test2Service,
        Test2PopupService,
        Test2ResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhispterTest2Module {}
