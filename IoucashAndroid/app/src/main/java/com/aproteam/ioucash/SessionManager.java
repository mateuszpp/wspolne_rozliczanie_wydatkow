package com.aproteam.ioucash;

import android.annotation.SuppressLint;
import android.content.Context;

import com.aproteam.ioucash.model.User;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/**
 * Class for managing logged in user session data.
 */
@SuppressLint("StaticFieldLeak")
public class SessionManager {

	private static SessionManager instance;
	private final Context context;

	private SessionManager(Context context) {
		this.context = context;
	}

	/**
	 * Gets an instance of the SessionManager
	 *
	 * @param context the current context
	 * @return the SessionManager object
	 */
	public static SessionManager getInstance(Context context) {
		if (instance == null)
			instance = new SessionManager(context);
		return instance;
	}

	public boolean isLoggedIn() {
		return readUserData() != null;
	}

	/**
	 * Logs out the current user.
	 *
	 * @return true if the logout was successful, false if not
	 */
	public boolean logout() {
		return saveUserData(null);
	}

	/**
	 * Reads the user data from the session.
	 *
	 * @return User object containing the user data, or null if user data is not available or there is an error
	 */
	public User readUserData() {
		try {
			String json = FileUtils.readFileToString(getLocalSessionDataFile(), StandardCharsets.UTF_8);
			return new Gson().fromJson(json, User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves the user data to the session.
	 *
	 * @param user User object containing the user data to be saved
	 * @return true if the user data was saved successfully, false if not
	 */
	public boolean saveUserData(User user) {
		String json = new Gson().toJson(user);
		try (FileWriter fileWriter = new FileWriter(getLocalSessionDataFile())) {
			fileWriter.write(json);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @return the path to the local file with the session data
	 */
	public File getLocalSessionDataFile() {
		return new File(context.getFilesDir(), "userdata.json");
	}

	public Context getContext() {
		return context;
	}
}