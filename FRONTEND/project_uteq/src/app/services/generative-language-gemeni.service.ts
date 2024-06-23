import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenerativeLanguageGemeniService {
  private apiKey: string = 'AIzaSyCV836xtQAq5KekWFoxAyMOSmEbfWiu8dU';
  private apiUrl: string = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${this.apiKey}`;

  constructor(private http: HttpClient) {}

  generateContent(prompt: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = {
      contents: [
        {
          parts: [
            {
              text: prompt
            }
          ]
        }
      ]
    };
    return this.http.post(this.apiUrl, body, { headers });
  }
}
