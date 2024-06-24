import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-grammar-review',
  templateUrl: './grammar-review.component.html',
  styleUrls: ['./grammar-review.component.css']
})
export class GrammarReviewComponent {
  @Input() original: string = '';
  @Input() alternativas: string[] = [];
  @Output() obtenerAlternativas: EventEmitter<void> = new EventEmitter<void>();
  lista_alternativas: string[]=[];
  onButtonClick(): void {
    this.obtenerAlternativas.emit();
    
   
  }
  separador()
  {
   
  }
}
