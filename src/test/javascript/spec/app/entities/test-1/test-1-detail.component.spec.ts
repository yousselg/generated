/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhispterTestModule } from '../../../test.module';
import { Test1DetailComponent } from '../../../../../../main/webapp/app/entities/test-1/test-1-detail.component';
import { Test1Service } from '../../../../../../main/webapp/app/entities/test-1/test-1.service';
import { Test1 } from '../../../../../../main/webapp/app/entities/test-1/test-1.model';

describe('Component Tests', () => {

    describe('Test1 Management Detail Component', () => {
        let comp: Test1DetailComponent;
        let fixture: ComponentFixture<Test1DetailComponent>;
        let service: Test1Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhispterTestModule],
                declarations: [Test1DetailComponent],
                providers: [
                    Test1Service
                ]
            })
            .overrideTemplate(Test1DetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Test1DetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test1Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Test1(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.test1).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
