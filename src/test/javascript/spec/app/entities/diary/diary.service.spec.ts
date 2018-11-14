/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { DiaryService } from 'app/entities/diary/diary.service';
import { IDiary, Diary, Type } from 'app/shared/model/diary.model';

describe('Service Tests', () => {
    describe('Diary Service', () => {
        let injector: TestBed;
        let service: DiaryService;
        let httpMock: HttpTestingController;
        let elemDefault: IDiary;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DiaryService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Diary(
                0,
                Type.MOVIE,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Diary', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Diary(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Diary', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        title: 'BBBBBB',
                        year: 'BBBBBB',
                        genre: 'BBBBBB',
                        writer: 'BBBBBB',
                        actors: 'BBBBBB',
                        plot: 'BBBBBB',
                        poster: 'BBBBBB',
                        imdbrating: 1,
                        impression: 'BBBBBB',
                        myrating: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Diary', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        title: 'BBBBBB',
                        year: 'BBBBBB',
                        genre: 'BBBBBB',
                        writer: 'BBBBBB',
                        actors: 'BBBBBB',
                        plot: 'BBBBBB',
                        poster: 'BBBBBB',
                        imdbrating: 1,
                        impression: 'BBBBBB',
                        myrating: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Diary', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
