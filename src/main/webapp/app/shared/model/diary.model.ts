export const enum Type {
    MOVIE = 'MOVIE',
    SERIES = 'SERIES'
}

export interface IDiary {
    id?: number;
    type?: Type;
    title?: string;
    year?: string;
    genre?: string;
    writer?: string;
    actors?: string;
    plot?: string;
    poster?: string;
    imdbrating?: number;
    impression?: string;
    myrating?: number;
}

export class Diary implements IDiary {
    constructor(
        public id?: number,
        public type?: Type,
        public title?: string,
        public year?: string,
        public genre?: string,
        public writer?: string,
        public actors?: string,
        public plot?: string,
        public poster?: string,
        public imdbrating?: number,
        public impression?: string,
        public myrating?: number
    ) {}
}
