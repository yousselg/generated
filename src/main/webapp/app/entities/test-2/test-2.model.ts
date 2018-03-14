import { BaseEntity } from './../../shared';

export class Test2 implements BaseEntity {
    constructor(
        public id?: number,
        public prop1?: number,
        public prop2?: string,
        public test1Id?: number,
    ) {
    }
}
