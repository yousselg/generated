/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhispterTestModule } from '../../../test.module';
import { Test2DetailComponent } from '../../../../../../main/webapp/app/entities/test-2/test-2-detail.component';
import { Test2Service } from '../../../../../../main/webapp/app/entities/test-2/test-2.service';
import { Test2 } from '../../../../../../main/webapp/app/entities/test-2/test-2.model';

describe('Component Tests', () => {

    describe('Test2 Management Detail Component', () => {
        let comp: Test2DetailComponent;
        let fixture: ComponentFixture<Test2DetailComponent>;
        let service: Test2Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhispterTestModule],
                declarations: [Test2DetailComponent],
                providers: [
                    Test2Service
                ]
            })
            .overrideTemplate(Test2DetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Test2DetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test2Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Test2(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.test2).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
