using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Google.Protobuf;

namespace android_xamarin
{
    /// <summary>
    /// Ayudante para manipular los Messages.
    /// </summary>
    public static class MessageHelper
    {
        /// <summary>
        /// Deserializa una instancia de IMessage.
        /// </summary>
        /// <typeparam name="T">Tipo de la instancia de IMessage.</typeparam>
        /// <param name="stream">Stream recibido del servidor.</param>
        /// <returns>Instancia de T.</returns>
        public static T Deserialize<T>(Stream stream) where T : IMessage<T>, new()
        {
            //T t = (T)Activator.CreateInstance(typeof(T));
            T t = new T();
            Func<T> func = new Func<T>(() => { return t; });
            var p = new MessageParser<T>(func);
            return p.ParseFrom(stream);
        }
    }
}