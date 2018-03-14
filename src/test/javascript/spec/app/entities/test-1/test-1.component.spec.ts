/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhispterTestModule } from '../../../test.module';
import { Test1Component } from '../../../../../../main/webapp/app/entities/test-1/test-1.component';
import { Test1Service } from '../../../../../../main/webapp/app/entities/test-1/test-1.service';
import { Test1 } from '../../../../../../main/webapp/app/entities/test-1/test-1.model';

describe('Component Tests', () => {

    describe('Test1 Management Component', () => {
        let comp: Test1Component;
        let fixture: ComponentFixture<Test1Component>;
        let service: Test1Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhispterTestModule],
                declarations: [Test1Component],
                providers: [
                    Test1Service
                ]
            })
            .overrideTemplate(Test1Component, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Test1Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test1Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Test1(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.test1S[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
