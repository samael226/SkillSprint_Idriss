import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-challenge-settings',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  settingsForm!: FormGroup;
  badges = ['Best Design', 'Most Creative', 'Best UI/UX'];
  selectedBadges: string[] = [];
  newBadge: string = '';

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.settingsForm = this.fb.group({
      enableAiCoach: [true],
      enablePowerUps: [true],
      extraTime: [false],
      lateSubmission: [false],
      autoFormatting: [true],
      earlyFinishBonus: [true],
      enableAiCodeReview: [true],
      publicLeaderboard: [true],
      awardXpPoints: [true],
      firstPlaceXp: [500],
      secondPlaceXp: [300],
      thirdPlaceXp: [150],
      participationXp: [50],
      maxParticipants: [100],
      challengeVisibility: ['public'],
      accessCode: [''],
      limitParticipants: [false],
      restrictedAccess: [false]
    });
  }

  addBadge(): void {
    if (this.newBadge.trim() && !this.selectedBadges.includes(this.newBadge.trim())) {
      this.selectedBadges.push(this.newBadge.trim());
      this.newBadge = '';
    }
  }

  removeBadge(badge: string): void {
    this.selectedBadges = this.selectedBadges.filter(b => b !== badge);
  }

  selectBadge(badge: string): void {
    if (!this.selectedBadges.includes(badge)) {
      this.selectedBadges.push(badge);
    } else {
      this.removeBadge(badge);
    }
  }

  saveSettings(): void {
    console.log('Form values:', this.settingsForm.value);
    console.log('Selected badges:', this.selectedBadges);
    // Here you would typically send the data to your API
  }
}
