import React from 'react';
import { Link } from 'react-router-dom';

const Home = ({ user }) => {
  return (
    <div className="text-center">
      <h1 className="display-4 mb-4">Welcome to OAuth2 Demo</h1>
      
      {user ? (
        <div>
          <p className="lead">You are logged in as {user.displayName || user.email}</p>
          <div className="mt-4">
            <Link to="/profile" className="btn btn-primary btn-lg me-3">
              View Profile
            </Link>
          </div>
        </div>
      ) : (
        <div>
          <p className="lead mb-4">Please sign in to continue</p>
          <div className="d-flex justify-content-center gap-3">
            <a 
              href="/oauth2/authorization/google" 
              className="btn btn-outline-danger"
            >
              <i className="bi bi-google me-2"></i>
              Sign in with Google
            </a>
            <a 
              href="/oauth2/authorization/github" 
              className="btn btn-outline-dark"
            >
              <i className="bi bi-github me-2"></i>
              Sign in with GitHub
            </a>
          </div>
        </div>
      )}
    </div>
  );
};

export default Home;
