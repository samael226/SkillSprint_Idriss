export interface Challenge {
  id: number;
  title: string;
  description: string;
  status: 'Active' | 'Upcoming' | 'Completed';
  teams: number;
  startDate: Date;
  endDate: Date;
} 