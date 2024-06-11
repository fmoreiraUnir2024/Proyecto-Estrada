import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import baseUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(this.isLoggedIn());

  constructor(private httpClient: HttpClient) { }

  public generateToken(loginData: any): Observable<any> {
    return this.httpClient.post(`${baseUrl}/auth/v1/generate-token`, loginData);
  }

  public loginUser(token: string): void {
    localStorage.setItem('token', token);
    this.loggedIn.next(true);
  }

  public isLoggedIn(): boolean {
    let tokenStr = localStorage.getItem('token');
    return !(tokenStr == undefined || tokenStr == '' || tokenStr == null);
  }

  public logout(): boolean {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.loggedIn.next(false);
    return true;
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }

  public setUser(user: any): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser(): any {
    let userStr = localStorage.getItem('user');
    if (userStr != null) {
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }

  public getCurrentUser(): Observable<any> {
    return this.httpClient.get(`${baseUrl}/auth/v1/actual-usuario`);
  }

  public get isLoggedIn$(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }
}
