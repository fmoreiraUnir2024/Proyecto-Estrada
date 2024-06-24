import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent {
  @Input() feedback: string = '';
  @Output() obtenerfeedback: EventEmitter<void> = new EventEmitter<void>();
  onButtonClick(): void {
    this.obtenerfeedback.emit();
  
  }
}
