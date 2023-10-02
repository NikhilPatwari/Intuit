import { BusinessProfile } from "./BusinessProfile";

export interface ProfileFetchResponse {
    body? : Body,
    status? : string,
    errors? : Array<string>
}

interface Body {
    payload? : BusinessProfile
}