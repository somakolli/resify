import {Company} from "./Company";
type Role = "User" | "Admin";
export class User {
    id: string;
    company: Company;
    email: string;
    permissions: Array<any>;
    role: Role;
}
