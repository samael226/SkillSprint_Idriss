import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { Challenge } from './challenge.model';
import { ChallengeService } from './challenge.service';
import { RequirementsComponent } from '../requirements/requirements.component';
import { ResourcesComponent } from '../resources/resources.component';
import { SettingsComponent } from '../settings/settings.component';
import { UsersComponent } from '../users/users.component';

// Interface for the challenge form
interface ChallengeForm {
  title: string;
  shortDescription: string;
  category: string;
  difficultyLevel: string;
  fullDescription: string;
  tags: string[];
  startDate: string;
  endDate: string;
  duration: number;
  durationUnit: 'hours' | 'days';
  timezone: string;
  registrationOpens: string;
  registrationCloses: string;
  allowTeams: boolean;
  minTeamSize: number;
  maxTeamSize: number;
  allowSolo: boolean;
}

@Component({
  selector: 'app-challenges',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RequirementsComponent,
    ResourcesComponent,
    SettingsComponent,
    UsersComponent
  ],
  templateUrl: './challenges.component.html',
  styleUrls: ['./challenges.component.css']
})
export class ChallengesComponent implements OnInit {
  challenges: Challenge[] = [];
  searchTerm: string = '';
  totalChallenges: number = 0;
  currentPage: number = 1;
  itemsPerPage: number = 5;
  
  // Challenge form properties
  challengeForm!: FormGroup;
  showCreateForm: boolean = false;
  activeTab = 'details';
  categories = ['Web Development', 'Mobile Development', 'Data Science', 'UI/UX Design', 'Cybersecurity', 'DevOps'];
  difficultyLevels = ['Beginner', 'Intermediate', 'Advanced', 'Expert'];
  timezones = ['UTC (Coordinated Universal Time)', 'EST (Eastern Standard Time)', 'PST (Pacific Standard Time)'];
  availableTags = ['React', 'NodeJS', 'API', 'Database', 'Cloud', 'Mobile', 'Security', 'Design', 'Frontend', 'Backend'];

  constructor(
    private challengeService: ChallengeService, 
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadChallenges();
    this.initForm();
  }

  loadChallenges(): void {
    this.challengeService.getChallenges().subscribe(data => {
      this.challenges = data;
      this.totalChallenges = data.length;
    });
  }

  get filteredChallenges(): Challenge[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    
    return this.challenges
      .filter(challenge => 
        challenge.title.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
        challenge.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      )
      .slice(startIndex, endIndex);
  }

  get displayedRange(): string {
    const startItem = (this.currentPage - 1) * this.itemsPerPage + 1;
    const endItem = Math.min(startItem + this.filteredChallenges.length - 1, this.totalChallenges);
    return `${startItem} of ${this.totalChallenges}`;
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage(): void {
    const totalPages = Math.ceil(this.challenges.length / this.itemsPerPage);
    if (this.currentPage < totalPages) {
      this.currentPage++;
    }
  }

  isLastPage(): boolean {
    return this.currentPage >= Math.ceil(this.challenges.length / this.itemsPerPage);
  }

  formatDateRange(startDate: Date, endDate: Date): string {
    const options: Intl.DateTimeFormatOptions = { month: 'short', day: 'numeric' };
    const start = startDate.toLocaleDateString('en-US', options);
    const end = endDate.toLocaleDateString('en-US', options);
    const year = endDate.getFullYear();
    return `${start}-${end.split(' ')[1]}, ${year}`;
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Active':
        return 'bg-green-100 text-green-800 ring-1 ring-green-600/20';
      case 'Upcoming':
        return 'bg-indigo-100 text-indigo-800 ring-1 ring-indigo-600/20';
      case 'Completed':
        return 'bg-purple-100 text-purple-800 ring-1 ring-purple-600/20';
      default:
        return 'bg-gray-100 text-gray-800 ring-1 ring-gray-600/20';
    }
  }

  // Challenge form methods
  toggleCreateForm(): void {
    this.showCreateForm = !this.showCreateForm;
    if (this.showCreateForm) {
      this.initForm();
    }
  }

  initForm(): void {
    this.challengeForm = this.fb.group({
      title: ['', [Validators.required]],
      shortDescription: ['', [Validators.required, Validators.maxLength(200)]],
      category: ['', Validators.required],
      difficultyLevel: ['', Validators.required],
      fullDescription: ['', Validators.required],
      tags: this.fb.array([]),
      startDate: [''],
      endDate: [''],
      duration: [48],
      durationUnit: ['hours'],
      timezone: ['UTC (Coordinated Universal Time)'],
      registrationOpens: [''],
      registrationCloses: [''],
      allowTeams: [true],
      minTeamSize: [1],
      maxTeamSize: [4],
      allowSolo: [true]
    });
  }

  // Handle team participation toggle
  onTeamsAllowedChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const teamsAllowed = checkbox.checked;
    
    // If teams are not allowed, disable team size fields
    if (!teamsAllowed) {
      this.challengeForm.get('minTeamSize')?.disable();
      this.challengeForm.get('maxTeamSize')?.disable();
    } else {
      this.challengeForm.get('minTeamSize')?.enable();
      this.challengeForm.get('maxTeamSize')?.enable();
    }
    
    // If neither teams nor solo is allowed, force solo to be true
    if (!teamsAllowed && !this.challengeForm.get('allowSolo')?.value) {
      this.challengeForm.get('allowSolo')?.setValue(true);
    }
  }
  
  // Handle solo participation toggle
  onSoloAllowedChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const soloAllowed = checkbox.checked;
    
    // If solo is not allowed and teams are not allowed, force teams to be true
    if (!soloAllowed && !this.challengeForm.get('allowTeams')?.value) {
      this.challengeForm.get('allowTeams')?.setValue(true);
      // Since teams are now enabled, enable team size controls
      this.challengeForm.get('minTeamSize')?.enable();
      this.challengeForm.get('maxTeamSize')?.enable();
    }
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  get tagsArray(): FormArray {
    return this.challengeForm.get('tags') as FormArray;
  }

  addTag(tag: string): void {
    const currentTags = this.tagsArray.value;
    if (tag && !currentTags.includes(tag)) {
      this.tagsArray.push(this.fb.control(tag));
    }
  }

  removeTag(index: number): void {
    this.tagsArray.removeAt(index);
  }

  saveDraft(): void {
    console.log('Saving draft:', this.challengeForm.value);
    // Implement save draft functionality
  }

  publishChallenge(): void {
    if (this.challengeForm.valid) {
      console.log('Publishing challenge:', this.challengeForm.value);
      // Implement publish functionality
      this.showCreateForm = false;
    } else {
      // Mark all fields as touched to trigger validation
      Object.keys(this.challengeForm.controls).forEach(key => {
        const control = this.challengeForm.get(key);
        control?.markAsTouched();
      });
    }
  }

  cancelCreateForm(): void {
    this.showCreateForm = false;
  }

  HackathonForm(): void {
    this.showCreateForm = true;
  }
}
