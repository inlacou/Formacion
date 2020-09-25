package com.inlacou.fivedaysapp.multitasking;

public class MyRunnable implements Runnable {

		public boolean work = true;

		private Callback callback;

		public MyRunnable(Callback callback) {
			this.callback = callback;
		}

		@Override
		public void run() {
			while (work){
				callback.callback();
			}
		}

		public interface Callback {
			void callback();
		}

	}