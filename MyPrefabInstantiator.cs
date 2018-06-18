using UnityEngine;
using System.Collections;
using Vuforia;
using UnityEngine.UI;

public class MyPrefabInstantiator : MonoBehaviour, ITrackableEventHandler
{

    private TrackableBehaviour mTrackableBehaviour;

    public Transform myModelPrefab;
    public Transform myModelTrf;
    public GUIStyle bText;
    public GUIStyle targetStyle;
    public Transform building;
    public Transform buildingModel;
    public ApplicationModel buildingName;
    public GameObject persObj;
    private bool mShowGUIButton = false;
    private Rect mButtonRect = new Rect(0, Screen.height - 200, Screen.width, 200);
    


    // Use this for initialization
    void Start()
    {
        persObj = GameObject.FindGameObjectWithTag("PersistantObject");
        mTrackableBehaviour = GetComponent<TrackableBehaviour>();
        buildingName = persObj.GetComponent<ApplicationModel>();
        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

    }

    // Update is called once per frame
    void Update()
    {
    }

    public void OnTrackableStateChanged(
              TrackableBehaviour.Status previousStatus,
              TrackableBehaviour.Status newStatus)
    {
        

        if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED)
        {
            OnTrackingFound();
            mShowGUIButton = true;
            buildingName.currentBuilding = mTrackableBehaviour.name;
        }
        else
        {
            OnTrackingLost();
            mShowGUIButton = false;
        }
        
    }

    string text = "Information";


    private void OnGUI()
    {

        GUIStyle style = new GUIStyle("button");
 
        int chin = Screen.height / 5;
        int horizontalWidth = 100;
        int horizontalHeight = 20;

        // Top Left 
        GUI.Box(new Rect(0, chin, horizontalWidth, horizontalHeight), " ", targetStyle); //(Horizontal)
        GUI.Box(new Rect(0, chin, horizontalHeight, horizontalWidth), " ", targetStyle); //(Vertical)

        // Top Right
        GUI.Box(new Rect(Screen.width - horizontalWidth, chin, horizontalWidth, horizontalHeight), " ", targetStyle); //(Horizontal)
        GUI.Box(new Rect(Screen.width - horizontalHeight, chin, horizontalHeight, horizontalWidth), " ", targetStyle); //(Vertical)

        // Bottom Left
        GUI.Box(new Rect(0, Screen.height - chin - horizontalHeight, horizontalWidth, horizontalHeight), " ", targetStyle); //(Horizontal)
        GUI.Box(new Rect(0, Screen.height - chin - horizontalWidth, horizontalHeight, horizontalWidth), " ", targetStyle); //(Vertical)

        // Bottom Right
        GUI.Box(new Rect(Screen.width - horizontalWidth, Screen.height - chin - horizontalHeight, horizontalWidth, horizontalHeight), " ", targetStyle); //(Horizontal)
        GUI.Box(new Rect(Screen.width - horizontalHeight, Screen.height - chin - horizontalWidth, horizontalHeight, horizontalWidth), " ", targetStyle); //(Vertical)

        if (mShowGUIButton)
       {
            if (GUI.Button(mButtonRect, text, bText))
            {
                Application.LoadLevel("Info");
            }
        }
    }

    private void OnTrackingLost()
    {
        myModelTrf.gameObject.active = false;
        buildingModel.gameObject.active = false;
    }

    private void OnTrackingFound()
    {
        
        if (myModelPrefab != null)
        {
            // instantiate the viking
            myModelTrf = GameObject.Instantiate(myModelPrefab) as Transform;
            myModelTrf.parent = mTrackableBehaviour.transform;
            myModelTrf.localPosition = new Vector3(0f, 0f, -.2f);
            myModelTrf.localRotation = Quaternion.identity;
            myModelTrf.Rotate(270f, 180f, 0);
            myModelTrf.localScale = new Vector3(0.065f, 0.065f, 0.065f);

            myModelTrf.gameObject.active = true;

            // instantiate building name
            buildingModel = GameObject.Instantiate(building) as Transform;
            buildingModel.parent = mTrackableBehaviour.transform;
            buildingModel.localPosition = new Vector3(0f, 0f, 0.2f);
            buildingModel.localRotation = Quaternion.identity;
            buildingModel.Rotate(90f, 180f, 180f);
            buildingModel.localScale = new Vector3(0.02f, 0.02f, 0.02f);

            buildingModel.gameObject.active = true;
        }
    }
}