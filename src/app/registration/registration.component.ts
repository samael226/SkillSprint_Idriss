import { Component } from '@angular/core';
import { RouterOutlet , RouterLink} from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
  imports: [CommonModule, ReactiveFormsModule , RouterLink],


})
export class RegistrationComponent {
  signupForm: FormGroup;
  
  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      newsletter: [true]
    });
  }
  
  onSubmit() {
    if (this.signupForm.valid) {
      console.log('Form submitted', this.signupForm.value);
      // Add authentication service call here
    } else {
      this.signupForm.markAllAsTouched();
    }
  }
}
