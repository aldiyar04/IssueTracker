import { User } from "./user";

export interface UserLoginResponse {
  jwtToken: string;
  user: User
}
