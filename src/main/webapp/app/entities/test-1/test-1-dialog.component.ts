import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Test1 } from './test-1.model';
import { Test1PopupService } from './test-1-popup.service';
import { Test1Service } from './test-1.service';

@Component({
    selector: 'jhi-test-1-dialog',
    templateUrl: './test-1-dialog.component.html'
})
export class Test1DialogComponent implements OnInit {

    test1: Test1;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private test1Service: Test1Service,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.test1.id !== undefined) {
            this.subscribeToSaveResponse(
                this.test1Service.update(this.test1));
        } else {
            this.subscribeToSaveResponse(
                this.test1Service.create(this.test1));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Test1>>) {
        result.subscribe((res: HttpResponse<Test1>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Test1) {
        this.eventManager.broadcast({ name: 'test1ListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-test-1-popup',
    template: ''
})
export class Test1PopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private test1PopupService: Test1PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.test1PopupService
                    .open(Test1DialogComponent as Component, params['id']);
            } else {
                this.test1PopupService
                    .open(Test1DialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
