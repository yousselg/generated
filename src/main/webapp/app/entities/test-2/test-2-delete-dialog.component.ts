import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Test2 } from './test-2.model';
import { Test2PopupService } from './test-2-popup.service';
import { Test2Service } from './test-2.service';

@Component({
    selector: 'jhi-test-2-delete-dialog',
    templateUrl: './test-2-delete-dialog.component.html'
})
export class Test2DeleteDialogComponent {

    test2: Test2;

    constructor(
        private test2Service: Test2Service,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.test2Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'test2ListModification',
                content: 'Deleted an test2'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-2-delete-popup',
    template: ''
})
export class Test2DeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private test2PopupService: Test2PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.test2PopupService
                .open(Test2DeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
