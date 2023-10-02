import { Address } from "./Address";

export interface BusinessProfile {
    id? : string,
    companyName? : string,
    legalName? : string,
    legalAddress : Address,
    businessAddress : Address,
    pan? : string,
    ein? : string,
    email? : string,
    website? : string
}

