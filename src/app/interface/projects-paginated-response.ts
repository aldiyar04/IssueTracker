import { Project } from "./project";

export interface ProjectsPaginatedResponse {
  dtos: Project[];
  totalPages: number;
}
