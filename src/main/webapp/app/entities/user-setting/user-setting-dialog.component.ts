import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserSetting } from './user-setting.model';
import { UserSettingPopupService } from './user-setting-popup.service';
import { UserSettingService } from './user-setting.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-setting-dialog',
    templateUrl: './user-setting-dialog.component.html'
})
export class UserSettingDialogComponent implements OnInit {

    userSetting: UserSetting;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userSettingService: UserSettingService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userSetting.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userSettingService.update(this.userSetting));
        } else {
            this.subscribeToSaveResponse(
                this.userSettingService.create(this.userSetting));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserSetting>) {
        result.subscribe((res: UserSetting) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserSetting) {
        this.eventManager.broadcast({ name: 'userSettingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-setting-popup',
    template: ''
})
export class UserSettingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userSettingPopupService: UserSettingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userSettingPopupService
                    .open(UserSettingDialogComponent as Component, params['id']);
            } else {
                this.userSettingPopupService
                    .open(UserSettingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
