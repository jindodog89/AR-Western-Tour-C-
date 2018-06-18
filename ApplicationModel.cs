using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ApplicationModel:MonoBehaviour
{
    public string currentBuilding;

    void Awake()
    {
        DontDestroyOnLoad(this);
    }

}
