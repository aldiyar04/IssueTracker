import { HttpStatusCode } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { BehaviorSubject } from 'rxjs';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { DataState } from '../enum/data-state.enum';
import { UserRole } from '../enum/user-role.enum';
import { AppState } from '../interface/app-state';
import { Project } from '../interface/project';
import { ProjectCreateRequest } from '../interface/project-create-request';
import { ProjectDeleteRequest } from '../interface/project-delete-request';
import { ProjectUpdateRequest } from '../interface/project-update-request';
import { ProjectsPaginatedResponse } from '../interface/projects-paginated-response';
import { User } from '../interface/user';
import { ProjectService } from '../service/project.service';
import { StorageService } from '../service/storage.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {1
  appState$: Observable<AppState<ProjectsPaginatedResponse>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<ProjectsPaginatedResponse>(null);
  user: User;
  readonly UserRole = UserRole;

  constructor(private projectService: ProjectService, private notifier: NotifierService, private storageService: StorageService) {
    this.user = storageService.getUser();

    this.appState$ = this.projectService.projects$
    .pipe(
      map(response => {
        this.dataSubject.next(response);
        return { dataState: DataState.LOADED_STATE, appData: response };
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: Error) => {
        return of({ dataState: DataState.ERROR_STATE, error: error.message });
      })
    );
  }

  ngOnInit(): void {
  }

  createProject(createProjectForm: NgForm) {
    this.appState$ = this.projectService.create$(createProjectForm.value as ProjectCreateRequest)
    .pipe(
      map(projectResponse => {
        let projects = this.dataSubject.value.dtos;
        projects.unshift(projectResponse);

        let totalPages = this.dataSubject.value.totalPages;

        this.dataSubject.next({
          dtos: projects,
          totalPages: totalPages
        } as ProjectsPaginatedResponse);

        createProjectForm.reset();
        document.getElementById('closeCreateProjectModalBtn').click();
        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: Error) => {
        this.notifier.notify('error', error.message);
        return of({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value });
      })
    );
  }

  updateProject(updateProjectForm: NgForm) {
    let updateRequest = updateProjectForm.value as ProjectUpdateRequest

    this.appState$ = this.projectService.update$(updateRequest)
    .pipe(
      map(projectResponse => {
        this.replaceProjectInDataSubject(projectResponse);
        document.getElementById(`closeUpdateProject${updateRequest.id}ModalBtn`).click();
        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: Error) => {
        this.notifier.notify('error', error.message);
        return of({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value });
        // return of({ dataState: DataState.ERROR_STATE, error: error.message });
      })
    );
  }

  private replaceProjectInDataSubject(projectResponse : Project) {
    let projects = this.dataSubject.value.dtos;
    projects.forEach((project, index) => {
      if (project.id === projectResponse.id) {
        projects[index] = projectResponse;
      }
    });

    let totalPages = this.dataSubject.value.totalPages;

    this.dataSubject.next({
      dtos: projects,
      totalPages: totalPages
    } as ProjectsPaginatedResponse);
  }

  deleteProject(deleteProjectForm: NgForm) {
    let projectId = (deleteProjectForm.value as ProjectDeleteRequest).id;

    this.appState$ = this.projectService.delete$(projectId)
    .pipe(
      map(httpResponse => {
        if (httpResponse.status !== HttpStatusCode.NoContent) {
          throw new Error("Could not delete project " + projectId);
        }

        document.getElementById(`closeDeleteProject${projectId}ModalBtn`).click();

        let projects = this.dataSubject.value.dtos.filter(project => project.id !== projectId);
        let totalPages = this.dataSubject.value.totalPages;

        this.dataSubject.next({
          dtos: projects,
          totalPages: totalPages
        } as ProjectsPaginatedResponse);

        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: Error) => {
        this.notifier.notify('error', error.message);
        return of({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value });
      })
    );
  }
}
