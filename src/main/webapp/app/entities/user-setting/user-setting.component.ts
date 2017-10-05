import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { UserSetting } from './user-setting.model';
import { UserSettingService } from './user-setting.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-user-setting',
    templateUrl: './user-setting.component.html'
})
export class UserSettingComponent implements OnInit, OnDestroy {
userSettings: UserSetting[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userSettingService: UserSettingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userSettingService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userSettings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserSettings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserSetting) {
        return item.id;
    }
    registerChangeInUserSettings() {
        this.eventSubscriber = this.eventManager.subscribe('userSettingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
