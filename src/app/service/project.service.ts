import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ProjectsPaginatedResponse } from '../interface/projects-paginated-response';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ProjectCreateRequest } from '../interface/project-create-request';
import { Project } from '../interface/project';
import { ApiConstants } from 'src/api-constants';
import { Utils } from '../utils';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly apiUrl = ApiConstants.API_HOST + '/projects';

  constructor(private http: HttpClient) { }

  projects$ = <Observable<ProjectsPaginatedResponse>>
  this.http.get<ProjectsPaginatedResponse>(this.apiUrl)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );

  get$ = (projectId: number) => <Observable<Project>>
  this.http.get<Project>(`${this.apiUrl}/${projectId}`)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );

  create$ = (projectCreateReq: ProjectCreateRequest) => <Observable<Project>>
  this.http.post<Project>(this.apiUrl, projectCreateReq)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );

  update$ = (projectUpdateReq: ProjectCreateRequest) => <Observable<Project>>
  this.http.put<Project>(this.apiUrl, projectUpdateReq)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );

  delete$ = (projectId: number) => <Observable<HttpResponse<null>>>
  this.http.delete(`${this.apiUrl}/${projectId}`, {observe: 'response'})
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );
}
