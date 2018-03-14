import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhispterTest1Module } from './test-1/test-1.module';
import { JhispterTest2Module } from './test-2/test-2.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhispterTest1Module,
        JhispterTest2Module,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhispterEntityModule {}
