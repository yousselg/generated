import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Test2 } from './test-2.model';
import { Test2PopupService } from './test-2-popup.service';
import { Test2Service } from './test-2.service';
import { Test1, Test1Service } from '../test-1';

@Component({
    selector: 'jhi-test-2-dialog',
    templateUrl: './test-2-dialog.component.html'
})
export class Test2DialogComponent implements OnInit {

    test2: Test2;
    isSaving: boolean;

    test1s: Test1[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private test2Service: Test2Service,
        private test1Service: Test1Service,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.test1Service.query()
            .subscribe((res: HttpResponse<Test1[]>) => { this.test1s = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.test2.id !== undefined) {
            this.subscribeToSaveResponse(
                this.test2Service.update(this.test2));
        } else {
            this.subscribeToSaveResponse(
                this.test2Service.create(this.test2));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Test2>>) {
        result.subscribe((res: HttpResponse<Test2>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Test2) {
        this.eventManager.broadcast({ name: 'test2ListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTest1ById(index: number, item: Test1) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-test-2-popup',
    template: ''
})
export class Test2PopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private test2PopupService: Test2PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.test2PopupService
                    .open(Test2DialogComponent as Component, params['id']);
            } else {
                this.test2PopupService
                    .open(Test2DialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
