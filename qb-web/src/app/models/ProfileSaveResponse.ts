export interface ProfileSaveResponse {
    status?: String,
    errors?: Array<string>,
    body?: {
        payload?: any
    }
}