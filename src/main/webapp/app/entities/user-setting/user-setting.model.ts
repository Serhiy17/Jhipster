import { BaseEntity } from './../../shared';

export const enum SettingType {
    'USER',
    'SYSTEM'
}

export class UserSetting implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public type?: SettingType,
        public userId?: number,
    ) {
    }
}
