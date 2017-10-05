/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserSettingDetailComponent } from '../../../../../../main/webapp/app/entities/user-setting/user-setting-detail.component';
import { UserSettingService } from '../../../../../../main/webapp/app/entities/user-setting/user-setting.service';
import { UserSetting } from '../../../../../../main/webapp/app/entities/user-setting/user-setting.model';

describe('Component Tests', () => {

    describe('UserSetting Management Detail Component', () => {
        let comp: UserSettingDetailComponent;
        let fixture: ComponentFixture<UserSettingDetailComponent>;
        let service: UserSettingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [UserSettingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserSettingService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserSettingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserSettingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserSettingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserSetting(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userSetting).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
