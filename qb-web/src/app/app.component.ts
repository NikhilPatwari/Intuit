import { ToastrService } from 'ngx-toastr';
import { BusinessProfileService } from './connector/business-profile.service';
import { BusinessProfile } from './models/BusinessProfile';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private profileService: BusinessProfileService,
    private toastr: ToastrService
  ) { }
  title = 'qb-web';
  checked: boolean = false;
  isLoginPage: boolean = true;
  isNewProfile: boolean = false;
  businessId?: string = "";
  businessProfile: BusinessProfile = this.profileService.getDummyBusinessProfile();
  openCreatepage() {
    this.isLoginPage = false;
    this.isNewProfile = true;
    this.businessId = "";
    this.businessProfile = this.profileService.getDummyBusinessProfile();
  }
  openLoginPage() {
    this.isLoginPage = true;
    this.businessId = "";
    this.isNewProfile = true;
  }
  setBusinessAddressSameAsLegalAddress() {
    if (this.checked) {
      this.businessProfile.businessAddress.addressLine1 = this.businessProfile.legalAddress.addressLine1;
      this.businessProfile.businessAddress.addressLine2 = this.businessProfile.legalAddress.addressLine2;
      this.businessProfile.businessAddress.city = this.businessProfile.legalAddress.city;
      this.businessProfile.businessAddress.country = this.businessProfile.legalAddress.country;
      this.businessProfile.businessAddress.state = this.businessProfile.legalAddress.state;
      this.businessProfile.businessAddress.zip = this.businessProfile.legalAddress.zip;
    }
  }
  login(businessId: string) {
    this.profileService.getBusinessProfileById(businessId).subscribe((response) => {
      if (response.body?.payload) {
        this.businessProfile = response.body.payload;
        this.isNewProfile = false;
        this.isLoginPage = false;
      } else {
        this.toastr.error("Error")
      }
    }, (error: HttpErrorResponse) => {
      if (error.error?.errors?.length > 0) {
        this.toastr.error(error.error.errors[0]);
      } else {
        this.toastr.error("Unable to fetch business profile");
      }
    });
  }
  createBusinessProfile(){
    this.profileService.createBusinessProfile(this.businessProfile).subscribe((response) => {
      console.log(response);
      if(response.status === "SUCCESS"){
        this.businessId = this.businessProfile.id = response.body?.payload?.id;
        this.isNewProfile = false;
        this.toastr.success("Success");
      }
    },(error: HttpErrorResponse) =>{
      console.log(error)
      if(error.error?.errors?.length > 0){
        this.toastr.error(error.error.errors[0])
      }else{
        this.toastr.error("Service Unavailable")
      }
    })
  }

  updateBusinessProfile() {
    this.profileService.updateBusinessProfile(this.businessProfile).subscribe((response) =>{
      if(response.status === "SUCCESS"){
        this.toastr.success("Success");
      }
    },(error: HttpErrorResponse) =>{
      console.log(error)
      if(error.error?.errors?.length > 0){
        this.toastr.error(error.error.errors[0])
      }else{
        this.toastr.error("Service Unavailable")
      }
    })
  }
}
