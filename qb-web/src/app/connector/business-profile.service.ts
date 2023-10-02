import { BusinessProfile } from './../models/BusinessProfile';
import { ProfileFetchResponse } from '../models/ProfileFetchResponse';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProfileSaveResponse } from '../models/ProfileSaveResponse';

@Injectable({
  providedIn: 'root'
})
export class BusinessProfileService {

  constructor(private http: HttpClient) { }

  getDummyBusinessProfile(): BusinessProfile {
    return {
      "businessAddress": {
        addressLine1: "",
        addressLine2: "",
        city: "",
        state: "",
        zip: "",
        country: ""
      },
      "legalAddress": {
        addressLine1: "",
        addressLine2: "",
        city: "",
        state: "",
        zip: "",
        country: ""
      }
    }
  }

  getBusinessProfileById(id: string) {
    return this.http.get<ProfileFetchResponse>("http://localhost:8081/api/businessProfile/findById?id=" + id)
  }

  updateBusinessProfile(profile: BusinessProfile) {
    return this.http.put<ProfileSaveResponse>("http://localhost:8081/api/businessProfile/update?product=" + "Quickbooks", profile)
  }
  createBusinessProfile(profile: BusinessProfile) {
    return this.http.post<ProfileSaveResponse>("http://localhost:8081/api/businessProfile/create?product=" + "Quickbooks", profile)
  }
  deletebusinessProfile(id: string) {
    return this.http.delete<ProfileSaveResponse>("http://localhost:8081/api/businessProfile/delete?id="+id)
  }

}
