import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Test1 } from './test-1.model';
import { Test1PopupService } from './test-1-popup.service';
import { Test1Service } from './test-1.service';

@Component({
    selector: 'jhi-test-1-delete-dialog',
    templateUrl: './test-1-delete-dialog.component.html'
})
export class Test1DeleteDialogComponent {

    test1: Test1;

    constructor(
        private test1Service: Test1Service,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.test1Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'test1ListModification',
                content: 'Deleted an test1'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-1-delete-popup',
    template: ''
})
export class Test1DeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private test1PopupService: Test1PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.test1PopupService
                .open(Test1DeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
