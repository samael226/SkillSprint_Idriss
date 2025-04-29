import { Component } from '@angular/core';
import { CommonModule, TitleCasePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-hackathon-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, TitleCasePipe],
  templateUrl: './hackathon-form.component.html',
  styleUrls: ['./hackathon-form.component.css']
})
export class HackathonFormComponent {
  hackathonForm: FormGroup;
  activeTab: string = 'basic';
  availableTags: string[] = ['Sustainability', 'AI', 'Web3', 'IoT', 'Cloud', 'Mobile', 'Data Science', 'AR/VR'];
  selectedTags: string[] = ['Sustainability', 'Web3', 'IoT'];

  constructor(private fb: FormBuilder) {
    this.hackathonForm = this.fb.group({
      title: ['Technovate 2025', Validators.required],
      tagline: ['Building Tomorrow\'s Solutions Today', Validators.required],
      description: ['Technovate 2025 is a premier hackathon bringing together developers, designers, and innovators to solve real-world problems. Join us for 48 hours of coding, collaboration, and creativity as teams compete to build cutting-edge solutions across multiple challenge tracks.', Validators.required],
      format: ['Hybrid', Validators.required],
      theme: ['Sustainable Technology', Validators.required],
      virtualEnabled: [true],
      virtualUrl: ['https://technovate25.devplanet.dev', Validators.required],
      physicalEnabled: [true],
      venueName: ['Technical Conference Center', Validators.required],
      address: ['123 Innovation Drive, San Francisco, CA 94107, United States', Validators.required],
      city: ['San Francisco', Validators.required],
      state: ['California', Validators.required],
      country: ['United States', Validators.required],
      capacity: [250, [Validators.required, Validators.min(1)]]
    });
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  addTag(tag: string): void {
    if (!this.selectedTags.includes(tag) && tag.trim() !== '') {
      this.selectedTags.push(tag);
    }
  }

  removeTag(tag: string): void {
    this.selectedTags = this.selectedTags.filter(t => t !== tag);
  }

  addCustomTag(event: any): void {
    if (event.key === 'Enter' && event.target.value.trim() !== '') {
      this.addTag(event.target.value.trim());
      event.target.value = '';
      event.preventDefault();
    }
  }

  saveDraft(): void {
    console.log('Saving draft:', this.hackathonForm.value);
    // Implement save draft logic
  }

  publishHackathon(): void {
    if (this.hackathonForm.valid) {
      console.log('Publishing hackathon:', this.hackathonForm.value);
      // Implement publish logic
    } else {
      this.markFormGroupTouched(this.hackathonForm);
    }
  }

  private markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if ((control as any).controls) {
        this.markFormGroupTouched(control as FormGroup);
      }
    });
  }
}