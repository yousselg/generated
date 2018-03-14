/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhispterTestModule } from '../../../test.module';
import { Test2Component } from '../../../../../../main/webapp/app/entities/test-2/test-2.component';
import { Test2Service } from '../../../../../../main/webapp/app/entities/test-2/test-2.service';
import { Test2 } from '../../../../../../main/webapp/app/entities/test-2/test-2.model';

describe('Component Tests', () => {

    describe('Test2 Management Component', () => {
        let comp: Test2Component;
        let fixture: ComponentFixture<Test2Component>;
        let service: Test2Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhispterTestModule],
                declarations: [Test2Component],
                providers: [
                    Test2Service
                ]
            })
            .overrideTemplate(Test2Component, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Test2Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test2Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Test2(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.test2S[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
