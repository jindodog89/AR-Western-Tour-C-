using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LogoLoader : MonoBehaviour {

	// Use this for initialization
	void Start () {
		StartCoroutine ("Countdown");
		//UnityEngine.SceneManagement.SceneManager.LoadScene(0);
	}
	
	private IEnumerator Countdown(){
		yield return new WaitForSeconds (4);
		Application.LoadLevel (1);
		//UnityEngine.SceneManagement.SceneManager.LoadScene(1);
	}
}
