import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  status: string;
  lastActive: Date;
  joined: string;
}

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  totalUsers = 2543;
  userGrowth = '+156 this month';
  activeUsers = 12;
  activeGrowth = '+3 from last month';
  reportedIssues = 7;
  issuesChange = '-2 from last week';
  
  users: User[] = [
    {
      id: 1,
      name: 'John Doe',
      email: 'john@example.com',
      role: 'Admin',
      status: 'Active',
      lastActive: new Date('2024-03-15'),
      joined: '2024-03-15'
    },
    {
      id: 2,
      name: 'Jane Smith',
      email: 'jane@example.com',
      role: 'User',
      status: 'Active',
      lastActive: new Date('2024-03-14'),
      joined: '2024-03-14'
    },
    {
      id: 3,
      name: 'Bob Johnson',
      email: 'bob@example.com',
      role: 'User',
      status: 'Inactive',
      lastActive: new Date('2024-03-10'),
      joined: '2024-03-10'
    },
    {
      id: 4,
      name: 'Alice Brown',
      email: 'alice@example.com',
      role: 'Judge',
      status: 'Active',
      lastActive: new Date('2024-03-16'),
      joined: '2024-03-16'
    },
    {
      id: 5,
      name: 'Charlie Wilson',
      email: 'charlie@example.com',
      role: 'User',
      status: 'Active',
      lastActive: new Date('2024-03-13'),
      joined: '2024-03-13'
    },
    {
      id: 6,
      name: 'Diana Miller',
      email: 'diana@example.com',
      role: 'Admin',
      status: 'Active',
      lastActive: new Date('2024-03-12'),
      joined: '2024-03-12'
    },
    {
      id: 7,
      name: 'Edward Davis',
      email: 'edward@example.com',
      role: 'User',
      status: 'Inactive',
      lastActive: new Date('2024-03-09'),
      joined: '2024-03-09'
    },
    {
      id: 8,
      name: 'Fiona Clark',
      email: 'fiona@example.com',
      role: 'Judge',
      status: 'Active',
      lastActive: new Date('2024-03-11'),
      joined: '2024-03-11'
    },
    {
      id: 9,
      name: 'George White',
      email: 'george@example.com',
      role: 'User',
      status: 'Active',
      lastActive: new Date('2024-03-08'),
      joined: '2024-03-08'
    },
    {
      id: 10,
      name: 'Hannah Lee',
      email: 'hannah@example.com',
      role: 'Admin',
      status: 'Inactive',
      lastActive: new Date('2024-03-07'),
      joined: '2024-03-07'
    }
  ];

  searchTerm: string = '';
  filteredUsers: User[] = [];

  ngOnInit() {
    this.filteredUsers = this.users;
  }

  onSearch() {
    if (!this.searchTerm) {
      this.filteredUsers = this.users;
      return;
    }

    const searchLower = this.searchTerm.toLowerCase();
    this.filteredUsers = this.users.filter(user => 
      user.name.toLowerCase().includes(searchLower) ||
      user.email.toLowerCase().includes(searchLower) ||
      user.role.toLowerCase().includes(searchLower)
    );
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Active':
        return 'bg-green-100 text-green-800 ring-1 ring-green-600/20';
      case 'Inactive':
        return 'bg-gray-100 text-gray-800 ring-1 ring-gray-600/20';
      default:
        return 'bg-gray-100 text-gray-800 ring-1 ring-gray-600/20';
    }
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  }
}
