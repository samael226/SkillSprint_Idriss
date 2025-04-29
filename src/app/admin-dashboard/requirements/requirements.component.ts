import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-requirements',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './requirements.component.html',
  styleUrls: ['./requirements.component.css']
})
export class RequirementsComponent {
  requirements: { title: string; description: string; completed: boolean }[] = [];

  criteria: { title: string; description: string; points: number; completed: boolean }[] = [];

  submissionOptions = {
    githubRepository: true,
    deploymentURL: true,
    projectDescription: true,
    demoVideo: true
  };

  addRequirement() {
    this.requirements.push({
      title: '',
      description: '',
      completed: false
    });
  }

  addCriteria() {
    this.criteria.push({
      title: '',
      description: '',
      points: 0,
      completed: false
    });
  }

  saveChanges() {
    console.log('Changes saved!');
    // Implement API save logic here
  }

  cancelChanges() {
    console.log('Changes canceled!');
    // Implement cancel logic here
  }

  deleteRequirement(index: number) {
    this.requirements.splice(index, 1);
  }

  deleteCriteria(index: number) {
    this.criteria.splice(index, 1);
  }
}
