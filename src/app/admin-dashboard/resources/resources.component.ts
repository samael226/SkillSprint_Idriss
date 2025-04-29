import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Resource {
  id: number;
  title: string;
  url: string;
  description: string;
  type: string;
}

interface Technology {
  id: number;
  name: string;
  category: string;
  required: boolean;
  description: string;
}

@Component({
  selector: 'app-resources',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent {
  // Starter Resources
  starterResources: Resource[] = [
    {
      id: 1,
      title: 'Starter Repository',
      url: 'https://github.com/example/collaborative-tool-starter',
      description: 'A starter repository with the basic setup for a real-time collaboration tool, including React, Socket.io, and Express.',
      type: 'GitHub'
    },
    {
      id: 2,
      title: 'API Documentation',
      url: 'https://docs.example.com/collaboration-api',
      description: 'Documentation for the collaboration API that can be used to implement real-time features.',
      type: 'Documentation'
    }
  ];

  // Technologies
  technologies: Technology[] = [
    {
      id: 1,
      name: 'React',
      category: 'Frontend',
      required: true,
      description: 'Frontend Framework'
    },
    {
      id: 2,
      name: 'Socket.io',
      category: 'Backend',
      required: true,
      description: 'Real-time Communication'
    },
    {
      id: 3,
      name: 'Express.js',
      category: 'Backend',
      required: true,
      description: 'Backend Framework'
    },
    {
      id: 4,
      name: 'MongoDB',
      category: 'Database',
      required: true,
      description: 'Database'
    }
  ];

  // Learning Resources
  learningResources: Resource[] = [
    {
      id: 1,
      title: 'Real-time Web Applications Tutorial',
      url: 'https://learn.example.com/dev/real-time-apps',
      description: 'A comprehensive tutorial on building real-time web applications with Socket.io and React.',
      type: 'Tutorial'
    },
    {
      id: 2,
      title: 'Collaborative Editing Best Practices',
      url: 'https://blog.example.com/collab-editing',
      description: 'An article discussing best practices for implementing collaborative editing features in web applications.',
      type: 'Article'
    }
  ];

  addStarterResource() {
    const newResource: Resource = {
      id: this.generateId(this.starterResources),
      title: '',
      url: '',
      description: '',
      type: 'GitHub'
    };
    this.starterResources.push(newResource);
  }

  addTechnology() {
    const newTech: Technology = {
      id: this.generateId(this.technologies),
      name: '',
      category: 'Frontend',
      required: false,
      description: ''
    };
    this.technologies.push(newTech);
  }

  addLearningResource() {
    const newResource: Resource = {
      id: this.generateId(this.learningResources),
      title: '',
      url: '',
      description: '',
      type: 'Tutorial'
    };
    this.learningResources.push(newResource);
  }

  deleteResource(resources: any[], id: number) {
    const index = resources.findIndex(r => r.id === id);
    if (index !== -1) {
      resources.splice(index, 1);
    }
  }

  toggleRequired(tech: Technology) {
    tech.required = !tech.required;
  }

  private generateId(items: any[]): number {
    return items.length > 0 ? Math.max(...items.map(item => item.id)) + 1 : 1;
  }
}
