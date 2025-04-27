import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, UsersComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  totalUsers = 2543;
  userGrowth = '+156 this month';
  activeUsers = 12;
  activeGrowth = '+3 from last month';
  reportedIssues = 7;
  issuesChange = '-2 from last week';
  
  // Add active tab tracking
  activeTab: 'users' | 'challenges' = 'users';
  
  setActiveTab(tab: 'users' | 'challenges' ) {
    this.activeTab = tab;
  }
  users = [
    { 
      name: 'John Doe', 
      email: 'john.doe@example.com', 
      role: 'User', 
      status: 'Active', 
      joined: 'Jan 12, 2025' 
    },
    { 
      name: 'Jane Smith', 
      email: 'jane.smith@example.com', 
      role: 'Judge', 
      status: 'Active', 
      joined: 'Feb 3, 2025' 
    },
    { 
      name: 'Robert Johnson', 
      email: 'robert.johnson@example.com', 
      role: 'Admin', 
      status: 'Active', 
      joined: 'Dec 5, 2024' 
    },
    { 
      name: 'Alice Lee', 
      email: 'alice.lee@example.com', 
      role: 'User', 
      status: 'Pending', 
      joined: 'Mar 18, 2025' 
    },
    { 
      name: 'Tom Wilson', 
      email: 'tom.wilson@example.com', 
      role: 'User', 
      status: 'Suspended', 
      joined: 'Feb 22, 2025' 
    }
  ];

  getStatusClass(status: string): string {
    switch(status) {
      case 'Active':
        return 'bg-green-100 text-green-800';
      case 'Pending':
        return 'bg-yellow-100 text-yellow-800';
      case 'Suspended':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

}
