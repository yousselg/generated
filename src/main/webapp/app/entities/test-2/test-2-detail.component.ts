import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Test2 } from './test-2.model';
import { Test2Service } from './test-2.service';

@Component({
    selector: 'jhi-test-2-detail',
    templateUrl: './test-2-detail.component.html'
})
export class Test2DetailComponent implements OnInit, OnDestroy {

    test2: Test2;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private test2Service: Test2Service,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTest2S();
    }

    load(id) {
        this.test2Service.find(id)
            .subscribe((test2Response: HttpResponse<Test2>) => {
                this.test2 = test2Response.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTest2S() {
        this.eventSubscriber = this.eventManager.subscribe(
            'test2ListModification',
            (response) => this.load(this.test2.id)
        );
    }
}
