import React, { useState } from 'react';
import axios from 'axios';

const Profile = ({ user: initialUser }) => {
  const [user, setUser] = useState(initialUser);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({
    displayName: initialUser.displayName || '',
    bio: initialUser.bio || ''
  });
  const [message, setMessage] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        '/profile',
        {
          displayName: formData.displayName,
          bio: formData.bio
        },
        { withCredentials: true }
      );
      
      setUser(prev => ({
        ...prev,
        displayName: formData.displayName,
        bio: formData.bio
      }));
      
      setMessage('Profile updated successfully!');
      setEditing(false);
      
      // Clear success message after 3 seconds
      setTimeout(() => setMessage(''), 3000);
    } catch (error) {
      console.error('Error updating profile:', error);
      setMessage('Failed to update profile. Please try again.');
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-8 col-lg-6">
        <div className="card">
          <div className="card-header">
            <h2 className="h4 mb-0">Your Profile</h2>
          </div>
          <div className="card-body">
            {message && (
              <div className={`alert ${message.includes('success') ? 'alert-success' : 'alert-danger'}`}>
                {message}
              </div>
            )}
            
            <div className="text-center mb-4">
              {user.avatarUrl ? (
                <img 
                  src={user.avatarUrl} 
                  alt={user.displayName || user.email}
                  className="rounded-circle"
                  style={{ width: '100px', height: '100px', objectFit: 'cover' }}
                />
              ) : (
                <div 
                  className="rounded-circle bg-secondary d-inline-flex align-items-center justify-content-center"
                  style={{ width: '100px', height: '100px' }}
                >
                  <i className="bi bi-person text-white" style={{ fontSize: '3rem' }}></i>
                </div>
              )}
            </div>

            {editing ? (
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input 
                    type="email" 
                    className="form-control" 
                    id="email" 
                    value={user.email} 
                    disabled 
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="displayName" className="form-label">Display Name</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="displayName" 
                    name="displayName"
                    value={formData.displayName}
                    onChange={handleInputChange}
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="bio" className="form-label">Bio</label>
                  <textarea 
                    className="form-control" 
                    id="bio" 
                    name="bio"
                    rows="3"
                    value={formData.bio}
                    onChange={handleInputChange}
                  ></textarea>
                </div>
                
                <div className="d-flex justify-content-between">
                  <button 
                    type="button" 
                    className="btn btn-outline-secondary"
                    onClick={() => {
                      setEditing(false);
                      setFormData({
                        displayName: user.displayName || '',
                        bio: user.bio || ''
                      });
                    }}
                  >
                    Cancel
                  </button>
                  <button type="submit" className="btn btn-primary">
                    Save Changes
                  </button>
                </div>
              </form>
            ) : (
              <div>
                <div className="mb-3">
                  <h5 className="mb-1">Email</h5>
                  <p className="text-muted">{user.email}</p>
                </div>
                
                <div className="mb-3">
                  <h5 className="mb-1">Display Name</h5>
                  <p className="text-muted">{user.displayName || 'Not set'}</p>
                </div>
                
                <div className="mb-4">
                  <h5 className="mb-1">Bio</h5>
                  <p className="text-muted">{user.bio || 'No bio provided'}</p>
                </div>
                
                <button 
                  className="btn btn-primary"
                  onClick={() => setEditing(true)}
                >
                  Edit Profile
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
