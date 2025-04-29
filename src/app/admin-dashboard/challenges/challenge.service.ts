import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Challenge } from './challenge.model';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  private challenges: Challenge[] = [
    {
      id: 1,
      title: 'Real-time Collaboration Tool',
      description: 'Build a real-time collaboration tool for remote teams',
      status: 'Active',
      teams: 24,
      startDate: new Date('2025-04-18'),
      endDate: new Date('2025-04-19')
    },
    {
      id: 2,
      title: 'Data Analytics Dashboard',
      description: 'Create an interactive dashboard for data analytics',
      status: 'Active',
      teams: 18,
      startDate: new Date('2025-04-15'),
      endDate: new Date('2025-04-17')
    },
    {
      id: 3,
      title: 'Web Performance Optimization',
      description: 'Optimize a web application for maximum performance',
      status: 'Upcoming',
      teams: 12,
      startDate: new Date('2025-05-15'),
      endDate: new Date('2025-05-17')
    },
    {
      id: 4,
      title: 'Mobile App Prototype',
      description: 'Design and prototype a mobile application',
      status: 'Completed',
      teams: 32,
      startDate: new Date('2025-03-25'),
      endDate: new Date('2025-03-27')
    },
    {
      id: 5,
      title: 'E-commerce API',
      description: 'Build a RESTful API for an e-commerce platform',
      status: 'Completed',
      teams: 28,
      startDate: new Date('2025-04-10'),
      endDate: new Date('2025-04-12')
    }
  ];

  getChallenges(): Observable<Challenge[]> {
    return of(this.challenges);
  }
} 